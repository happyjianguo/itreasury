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

		//SELECT[ADD BY kewenhu]��־ǿ[2004-07-09]
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

		//FROM[ADD BY kewenhu]��־ǿ[2004-07-09]
		sbFrom.append("	(SELECT --��PrintSecuritiesProfitAndLossFundBean��ƴд \n");
		sbFrom.append("		sec_DailyStock.clientID				clientID, --ҵ��λ \n");
		sbFrom.append("		sec_DailyStock.accountID			accountID, --�ʽ��ʻ� \n");
		sbFrom.append("		sec_DailyStock.securitiesID			securitiesID, --֤ȯID \n");
		sbFrom.append("		sec_DailyStock.quantity				quantity, --֤ȯ��� \n");
		sbFrom.append("		sec_DailyStock.netCost				netCost,--��λ�ɱ� ���۸� \n");
		sbFrom.append("		sec_DailyStock.cost					cost, --ʵ�ʳɱ� ȫ�� \n");
		sbFrom.append("		sec_Securities.shortName			securitiesName, --֤ȯ���� \n");
		sbFrom.append("		sec_Securities.typeID				securitiesTypeID,-- ֤ȯ��� \n");
		sbFrom.append("		sec_Account.counterpartID			counterpartID,-- ���׶��� \n");
		sbFrom.append("		sec_DailyStock.stockDate			stockDate, -- ����ʱ�� \n");
		sbFrom.append("		sec_Account.stockHolderAccountID1	stockHolderAccountID1,-- �ɶ��ʻ� \n");
		sbFrom.append("		sec_Securities.securitiesCode2		securitiesCode -- ֤ȯ���� \n");
		sbFrom.append("	FROM sec_DailyStock, sec_Securities, sec_Account \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND sec_DailyStock.securitiesID = sec_Securities.ID \n");
		sbFrom.append("		AND sec_Securities.typeID IN (5,6) \n");
		sbFrom.append("		AND sec_DailyStock.accountID = sec_Account.ID \n");
		//ʱ��
		if (printParam.getDailyAccountDate() != null) {
			sbFrom.append("		AND sec_DailyStock.stockDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		}
		//ҵ��λID
		if (printParam.getClientId() > 0) {
			sbFrom.append("		AND sec_DailyStock.clientID = "+printParam.getClientId()+" \n");
		}
		//�������˾
		String[] counterpartids = printParam.getFundCounterPartIds() ;
		if (counterpartids != null && counterpartids.length > 0) {
			sbFrom.append("		AND sec_Account.counterpartID IN (");
			for(int i = 0; i < counterpartids.length-1; i++) {
				sbFrom.append(Long.parseLong( counterpartids[i])+",");
			}
			sbFrom.append(Long.parseLong( counterpartids[counterpartids.length -1])+") \n");
		}
		//֤ȯ���
		if (printParam.getSecuritiesTypeId() > 0) {
			sbFrom.append("		AND sec_Securities.typeID = "+printParam.getSecuritiesTypeId()+" \n");
		}
		//֤ȯ���� :ע�⣬��ʵ�������bourseSecuritiesName�洢����ID
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

		//WHERE[ADD BY kewenhu]��־ǿ[2004-07-09]
		sbWhere.append("1 = 1 \n");
		sbWhere.append("	AND a.securitiesCode = b.securitiesCode(+) \n");
		sbWhere.append("	AND NVL(a.quantity, 0.0) > 0 \n");
	}

	/**
	 * ��ӡ����
	 * @param  printParam
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader PrintSecuritiesProfitAndLossFund(SecuritiesProfitAndLossParam printParam) throws SecuritiesException {
		this.getSQL(printParam);
		//ȡ��PageLoader����
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		//��ʼ��PageLoader
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

		//SELECT[ADD BY kewenhu]��־ǿ[2004-07-09]
		sbSelect.append("	NVL(aa.clientID, -1)							clientID, \n");
		sbSelect.append("	NVL(aa.accountID, -1)							accountID, \n");
		sbSelect.append("	NVL(aa.securitiesID, -1)						securitiesID, \n");
		sbSelect.append("	NVL(aa.quantity, 0.0)							quantity, \n");
		sbSelect.append("	ROUND((cc.outAmount-aa.netCost*cc.outQuantity),2) unitProfitLoss, \n");
		sbSelect.append("	ROUND((cc.outAmount-aa.netCost*cc.outQuantity),2) unitNetProfitLoss, \n");
		sbSelect.append("	NVL(aa.cost, 0.0)								netCost, \n");
		sbSelect.append("	NVL(aa.cost, 0.0)								cost, \n");

		//��־ǿ(2004-10-27)Ϊ�����info��jspҳ���ȡֵ�������ע�͵�����д����
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

		//FROM[ADD BY kewenhu]��־ǿ[2004-07-09]
		sbFrom.append("	(SELECT --��PrintSecuritiesProfitAndLossStockBean��ƴд \n");
		sbFrom.append("		sec_DailyStock.clientID				clientID, --ҵ��λ \n");
		sbFrom.append("		sec_DailyStock.accountID			accountID, --�ʽ��ʻ� \n");
		sbFrom.append("		sec_DailyStock.securitiesID			securitiesID, --֤ȯID \n");
		sbFrom.append("		sec_DailyStock.quantity				quantity, --֤ȯ��� \n");
		sbFrom.append("		sec_DailyStock.cost					cost, --ʵ�ʳɱ� \n");
		sbFrom.append("		DECODE(NVL(sec_DailyStock.quantity,0.0),0,0, \n");
		sbFrom.append("		ROUND(NVL(sec_DailyStock.cost,0.0)/NVL(sec_DailyStock.quantity,0.0),2))	netCost,--��λ�ɱ� \n");
		sbFrom.append("		sec_Securities.shortName			securitiesName, --֤ȯ���� \n");
		sbFrom.append("		sec_Securities.typeID				securitiesTypeID,-- ֤ȯ��� \n");
		sbFrom.append("		sec_Account.counterpartID			counterpartID,-- ���׶��� \n");
		sbFrom.append("		sec_DailyStock.stockDate			stockDate, -- ����ʱ�� \n");
		sbFrom.append("		sec_Account.stockHolderAccountID1	stockHolderAccountID1,-- �ɶ��ʻ� \n");
		sbFrom.append("		sec_Securities.securitiesCode1		securitiesCode1, -- ֤ȯ���� \n");
		sbFrom.append("		sec_Securities.securitiesCode2		securitiesCode2 -- ֤ȯ���� \n");
		sbFrom.append("	FROM sec_DailyStock, sec_Securities, sec_Account \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND sec_DailyStock.securitiesID = sec_Securities.ID \n");
		sbFrom.append("		AND sec_Securities.typeID IN ("+SECConstant.SecuritiesType.ENCLOSED_FUND+","+SECConstant.SecuritiesType.MUTUAL_FUND+") \n");
		sbFrom.append("		AND sec_DailyStock.accountID = sec_Account.ID(+) \n");
		//ʱ��
		if (printParam.getDailyAccountDate() != null) {
			sbFrom.append("		AND sec_DailyStock.stockDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		}
		//ҵ��λID
		if (printParam.getClientId() > 0) {
			sbFrom.append("		AND sec_DailyStock.clientID = "+printParam.getClientId()+" \n");
		}
		//���׶���
		if (printParam.getCounterPartId() > 0) {
			sbFrom.append("		AND sec_Account.counterpartID = "+printParam.getCounterPartId()+" \n");
		}
		//֤ȯ���
		if (printParam.getSecuritiesTypeId() > 0) {
			sbFrom.append("		AND sec_Securities.typeID = "+printParam.getSecuritiesTypeId()+" \n");
		}
		//֤ȯ���� :ע�⣬��ʵ�������bourseSecuritiesName�洢����ID
		String[] securitiesIds_bank = printParam.getSecuritiesIds();
		if (securitiesIds_bank != null && securitiesIds_bank.length > 0) {
			sbFrom.append("		AND sec_DailyStock.securitiesID IN (");
			for (int i = 0; i < securitiesIds_bank.length-1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds_bank[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds_bank[securitiesIds_bank.length-1])+") \n");
		}
		//�ɶ��ʻ�
		String[] stockHolderAccountIds = printParam.getStockHolderAccountIds();
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append("		AND sec_Account.stockHolderAccountID1 IN (");
			for (int i = 0; i < stockHolderAccountIds.length-1; i++) {
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+") \n ");
		}
		//�ʽ��ʺ�
		String[] accountIds = printParam.getAccountIds();
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append("		AND sec_DailyStock.accountID IN (");
			for (int i = 0; i < accountIds.length-1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i])+",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])+") \n");
		}
		sbFrom.append("	) aa, \n");

		//WHERE[ADD BY kewenhu]��־ǿ[2004-07-09]
		sbFrom.append("	(SELECT \n");
		sbFrom.append("		securitiesCode, \n");
		sbFrom.append("		NVL(netClosePrice, 0.0) netClosePrice, \n");
		sbFrom.append("		NVL(closePrice, 0.0) closePrice \n");
		sbFrom.append("	FROM sec_SecuritiesMarket \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND closeDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		sbFrom.append("	) bb, \n");

		//WHERE[ADD BY kewenhu]��־ǿ[2004-10-26]
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
		//ҵ��λID
		if (printParam.getClientId() > 0) {
			sbFrom.append("		AND a.clientID = "+printParam.getClientId()+" \n");
		}
		//���׶���
		if (printParam.getCounterPartId() > 0) {
			sbFrom.append("		AND a.counterpartID = "+printParam.getCounterPartId()+" \n");
		}
		//֤ȯ���
		if (printParam.getSecuritiesTypeId() > 0) {
			sbFrom.append("		AND c.typeID = "+printParam.getSecuritiesTypeId()+" \n");
		}
		//֤ȯ���� :ע�⣬��ʵ�������bourseSecuritiesName�洢����ID
		securitiesIds_bank = printParam.getSecuritiesIds();
		if (securitiesIds_bank != null && securitiesIds_bank.length > 0) {
			sbFrom.append("		AND a.securitiesID IN (");
			for (int i = 0; i < securitiesIds_bank.length-1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds_bank[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds_bank[securitiesIds_bank.length-1])+") \n");
		}
		//�ʽ��ʺ�
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
		//WHERE[ADD BY kewenhu]��־ǿ[2004-07-09]
		sbWhere.append("1 = 1 \n");
		sbWhere.append("	AND aa.securitiesCode1 || aa.securitiesCode2 LIKE bb.securitiesCode(+) || '%' \n");
		sbWhere.append("	AND aa.clientID = cc.clientID(+) \n");
		sbWhere.append("	AND aa.accountID = cc.accountID(+) \n");
		sbWhere.append("	AND aa.securitiesID = cc.securitiesID(+) \n");
		sbWhere.append("	AND NVL(aa.quantity, 0.0) > 0 \n");
	}

	/**
	 * ��ӡ����
	 * @param  printParam
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader PrintSecuritiesProfitAndLossFundForCnmef(SecuritiesProfitAndLossParam printParam) throws SecuritiesException {
		this.getSQLForCnmef(printParam);
		//ȡ��PageLoader����
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		//��ʼ��PageLoader
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