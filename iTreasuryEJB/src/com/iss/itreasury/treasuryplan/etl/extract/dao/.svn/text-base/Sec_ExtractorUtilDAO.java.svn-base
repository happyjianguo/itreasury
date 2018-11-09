/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-21
 */

package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.setting.dataentity.SecuritiesInfo;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ActualAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastAmountInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Sec_ExtractorUtilDAO extends AbstractExtractorUtilDAO
{

	/**
	 * @throws Exception
	 */
	public Sec_ExtractorUtilDAO() throws Exception
	{

		super(Constant.ModuleType.SECURITIES);
	}


	
	/**
	 * 从交割单获取实际发生额信息
	 * @param startDate
	 * @param endDate
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public Collection getActualAmountFromDeliveryOrders(Timestamp startDate, Timestamp endDate, long officeID, long currencyID) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String strStartTime = startDate.toString().substring(0, 10);
		String strEndTime = endDate.toString().substring(0, 10);
		bufferSQL.append(" select a.netincome,a.transactiontypeid, a.code,b.scode clientcode,c.code acccode,d.name transName,e.name cpName, f.name secName , a.deliverydate deliverydate ,d.capitaldirection direction  \n"
				+ " from SEC_deliveryorder a, client b,sec_account c, sec_transactiontype d, sec_counterpart e, sec_securities f " + " where a.statusid>0 and a.OfficeID = " + officeID + " and a.CurrencyID =" + currencyID + " and a.deliverydate >= to_date('" + DataFormat.formatDate(startDate)
				+ "','yyyy-mm-dd')  and a.deliverydate <= to_date('" + DataFormat.formatDate(endDate)
				+ "','yyyy-mm-dd') and a.clientid = b.id and a.accountid = c.id and a.transactiontypeid = d.id and a.counterpartid = e.id  and a.securitiesid = f.id(+) and a.TransactionTypeID in  \n" 
				+ " ( select id from SEC_TransactionType where IsNeedNotifyForm=0 and  id not in( 1602 , 1605 , 5102 , 5602) )");//把中签的交易过滤掉,否则可能会重复取交易
		ArrayList resList = new ArrayList();
		try 
		{
			initDAO();
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			// log.debug("---------forecastDate:"+forecastDate);
			// localPS.setTimestamp(1, forecastDate);
			ResultSet localRS = executeQuery();
			
			System.out.println("=================== getActualAmountFromDeliveryOrders \n"+bufferSQL.toString());
			while (localRS.next()) 
			{
				ActualAmountInfo info = new ActualAmountInfo();
				info.setActualAmount(localRS.getDouble("netincome"));
				info.setClientCode(localRS.getString("clientcode"));
				info.setContractCode(localRS.getString("code"));
				info.setCounterpartName(localRS.getString("cpName"));
				info.setAccountCode(localRS.getString("acccode"));
				info.setSecuritiesName(localRS.getString("secName"));
				info.setAccountTypeId(localRS.getLong("transactiontypeid"));
				info.setTransactionName(localRS.getString("transName"));
				
				//info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				//金额方向和交易类型表中一致
				if( localRS.getLong("direction") == SECConstant.Direction.RECEIVE ||
					localRS.getLong("direction") == SECConstant.Direction.FINANCE_RECEIVE	)
				{
					info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				}
				else if( localRS.getLong("direction") == SECConstant.Direction.PAY ||
					localRS.getLong("direction") == SECConstant.Direction.FINANCE_PAY	)
				{
					info.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				}
				else
				{
					info.setAmountDirection(0);
				}
				
				
				info.setOfficeID(officeID);
				info.setCurrencyID(currencyID);
				info.setModuleID(TPConstant.ModuleType.SECURITIES);
				info.setRemark(info.getContractCode());
				info.setTransactionDate(localRS.getTimestamp("deliverydate"));
				info.setTransactionCode(localRS.getString("code"));
				log.debug("---------getActualAmountFromDeliveryOrders:" + info);
				resList.add(info);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return resList;
	}


	/**
	 * 从申请书获取实际发生额信息
	 */
	public Collection getActualAmountFromApplyForms(Timestamp startDate, Timestamp endDate, long officeID, long currencyID) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String strStartTime = startDate.toString().substring(0, 10);
		String strEndTime = endDate.toString().substring(0, 10);
		bufferSQL.append("select a.code appcode,b.scode clientcode,c.code acccode,d.name transName,e.name cpName, f.name secName,a.TransactionTypeID,g.dtExecute,g.mAmount,a.code,g.sTransNo  ,d.capitaldirection direction  \n"
				+ "from SEC_NOTICEFORM a, sett_TransSecurities g,client b,sec_account c, sec_transactiontype d, sec_counterpart e, sec_securities f,SEC_DELIVERYORDER h   \n" + " where g.nofficeid=" + officeID + " and g.ncurrencyid=" + currencyID + " and g.nstatusid>=3 and g.dtExecute  >=  to_date('"
				+ DataFormat.formatDate(startDate) + "','yyyy-mm-dd')  and g.dtExecute <= to_date('" + DataFormat.formatDate(endDate)
				+ "','yyyy-mm-dd')   and g.nSecuritiesNoticeID=a.id and h.clientid = b.id and h.accountid = c.id(+) and a.transactiontypeid = d.id and h.counterpartid = e.id(+)  and h.securitiesid = f.id(+) and a.DELIVERYORDERID = h.id   \n"
				+ " and  d.id not in( 1602 , 1605 , 5102 , 5602)");//把中签的交易过滤掉,否则可能会重复取交易
		ArrayList resList = new ArrayList();
		try 
		{
			initDAO();
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			// log.debug("---------forecastDate:"+forecastDate);

			ResultSet localRS = executeQuery();
			System.out.println("=================== getActualAmountFromApplyForms \n"+bufferSQL.toString());
			while (localRS.next()) 
			{
				ActualAmountInfo info = new ActualAmountInfo();
				info.setActualAmount(localRS.getDouble("mAmount"));
				info.setClientCode(localRS.getString("clientcode"));
				info.setContractCode(localRS.getString("code"));
				info.setCounterpartName(localRS.getString("cpName"));
				info.setAccountCode(localRS.getString("acccode"));
				info.setSecuritiesName(localRS.getString("secName"));
				info.setAccountTypeId(localRS.getLong("transactiontypeid"));
				info.setTransactionName(localRS.getString("transName"));
				
				//info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				//金额方向和交易类型表中一致
				if( localRS.getLong("direction") == SECConstant.Direction.RECEIVE ||
					localRS.getLong("direction") == SECConstant.Direction.FINANCE_RECEIVE	)
				{
					info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				}
				else if( localRS.getLong("direction") == SECConstant.Direction.PAY ||
					localRS.getLong("direction") == SECConstant.Direction.FINANCE_PAY	)
				{
					info.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				}
				else
				{
					info.setAmountDirection(0);
				}
				
				info.setRemark(info.getContractCode());
				info.setOfficeID(officeID);
				info.setCurrencyID(currencyID);
				info.setModuleID(TPConstant.ModuleType.SECURITIES);
				info.setTransactionDate(localRS.getTimestamp("dtExecute"));
				info.setTransactionCode(localRS.getString("appcode"));
				resList.add(info);
				log.debug("---------ActualAmountFromApplyForms:" + info);
			}
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return resList;
	}


	/**
	 * 选取有关的交易数据作为结果集 预测未开始或者已开始为结束的交易在预测日当日的预测数
	 */
	public Collection getRepurchaseForecastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forecastDate, long repurchaseType) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String strTransTypeFieldName = "";
		String strTransTypeFieldName1 = "";
		String forecastDateFieldName = "";
		String forecastDateFieldName1 = "";
		Timestamp date6 = null;

		String strExtractDate = transferTimestampToTo_DateString(extractDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);

		String endDateFieldName = "";
		long amountDirection = -1;

		if (repurchaseType == 1) 
		{
			strTransTypeFieldName = "1301,1303,2601,2603,2701";
			forecastDateFieldName = "a.deliverydate";
			endDateFieldName = " nvl(a.LIQUIDATERATE,0) ";
			amountDirection = TPConstant.AmountDirection.PLUS;
			strTransTypeFieldName1 = "2703";
			forecastDateFieldName1 = "a.transactiondate";
		}
		else 
		{
			strTransTypeFieldName = "1301,1303,2601,2603,2701";
			forecastDateFieldName = "r.MaturityDate";
			endDateFieldName = " nvl(a.LIQUIDATERATE+a.term , 0) ";
			amountDirection = TPConstant.AmountDirection.SUBTRACT;
			strTransTypeFieldName1 = "2703";
			forecastDateFieldName1 = " r. MaturityDate+(a.deliverydate-a.transactiondate)";
		}

		/**
		 * and ((采集日期<=transactionstartdate and
		 * transactionstartdate+LiquidateRate=预测日期) or (采集日期
		 * >transactionstartdate and 采集日期<transactionenddate and 采集日期
		 * +LiquidateRate = 预测日期) )
		 */
		bufferSQL.append("select a.TRANSACTIONTYPEID,  b.scode clientcode,c.name cpname, d.name transname,e.code acccode,a.transactionstartdate,a.amount,a.code transacode \n ");
		bufferSQL.append(" from  SEC_ApplyForm   a, client b, sec_counterpart c, sec_transactiontype d, sec_account e \n ");
		bufferSQL.append(" where a.transactiontypeid in (" + strTransTypeFieldName + ") \n ");
		bufferSQL.append(" and a.statusid in (1,2) \n ");
		bufferSQL.append(" and a.clientid = b.id and c.id = a.Counterpartid(+) and  a.ACCOUNTID = e.id(+) and a.TransactionTypeID = d.id \n ");
		bufferSQL.append(" and( ( \n ");
		bufferSQL.append(" a.TRANSACTIONSTARTDATE >= " + strExtractDate + " and a.TRANSACTIONSTARTDATE+" + endDateFieldName + " = " + strForecastDate + " \n ");
		bufferSQL.append(" )or( \n ");
		bufferSQL.append(" a.TRANSACTIONSTARTDATE < " + strExtractDate + " and a.TRANSACTIONENDDATE > " + strExtractDate + " and " + strExtractDate + " + " + endDateFieldName + " = " + strForecastDate + " \n ");
		bufferSQL.append(" )) \n ");
		bufferSQL.append(" and a.OfficeID =" + officeID + " and a.CurrencyID =" + currencyID + " \n ");
		bufferSQL.append(" union \n ");
		bufferSQL.append(" select a.TRANSACTIONTYPEID, b.scode clientcode,c.name cpname,d.name transname,e.code acccode,r.valuedate,r.amount,a.code transacode \n ");
		bufferSQL.append(" from SEC_RepurchaseRegister r ,sec_deliveryorder a,client b, sec_counterpart c, sec_transactiontype d, sec_account e \n ");
		bufferSQL.append(" where " + forecastDateFieldName + "= " + strForecastDate + " and  r.statusid>0 and a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID + " and r.FirstDeliveryOrderID=a.id \n ");
		bufferSQL.append(" and a.clientid = b.id and c.id = a.Counterpartid(+) and  a.ACCOUNTID = e.id(+) and a.TransactionTypeID = d.id and a.TransactionTypeID in (" + strTransTypeFieldName + ")\n ");
		bufferSQL.append(" union \n ");
		bufferSQL.append(" select a.TRANSACTIONTYPEID, b.scode clientcode,c.name cpname,d.name transname,e.code acccode,r.valuedate,r.amount,a.code transacode \n ");
		bufferSQL.append(" from SEC_RepurchaseRegister r ,sec_deliveryorder a,client b, sec_counterpart c, sec_transactiontype d, sec_account e \n ");
		bufferSQL.append(" where " + forecastDateFieldName1 + " = " + strForecastDate + " and  r.statusid>0 and a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID + " and r.FirstDeliveryOrderID=a.id \n ");
		bufferSQL.append(" and a.clientid = b.id and c.id = a.Counterpartid(+) and  a.ACCOUNTID = e.id(+) and a.TransactionTypeID = d.id and a.TransactionTypeID in (" + strTransTypeFieldName1 + ")\n ");
		ArrayList resList;
		try 
		{
			initDAO();
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			// localPS.setTimestamp(1, extractDate);
			// localPS.setTimestamp(2, forecastDate);
			// localPS.setTimestamp(3, extractDate);
			// localPS.setTimestamp(4, extractDate);
			// localPS.setTimestamp(5, forecastDate);
			// localPS.setTimestamp(6, forecastDate);
			// localPS.setTimestamp(7, forecastDate);
			ResultSet localRS = executeQuery();
			resList = new ArrayList();
			while (localRS.next()) 
			{
				ForecastAmountInfo info = new ForecastAmountInfo();
				info.setForecastAmount(localRS.getDouble("amount"));
				info.setClientCode(localRS.getString("clientcode"));
				info.setContractCode(localRS.getString("transacode"));
				info.setCounterpartName(localRS.getString("cpName"));
				info.setAccountCode(localRS.getString("acccode"));
				info.setAccountTypeId(localRS.getLong("transactiontypeid") + 1);
				info.setTransactionName(localRS.getString("transName"));
				info.setAmountDirection(amountDirection);
				info.setRemark(info.getContractCode());
				info.setOfficeID(officeID);
				info.setCurrencyID(currencyID);
				info.setModuleID(TPConstant.ModuleType.SECURITIES);
				info.setTransactionDate(forecastDate);
				resList.add(info);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return resList;
	}


	public Collection getPurchaseForecastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forecastDate) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		
		String strCurrentDate = transferTimestampToTo_DateString(extractDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);

		String strTransTypeFieldName = "1601,1602,1603,1604,1605,1606,1701,1702,1703,1704,1705,1706,1707,1708,1801,1802,1803,1804,1805,1806,2101,2201,2202,2203,2204,3101,3201,3202,3203,3204,3301,3401,3402,3403,3404,3601,3701,3702,3703,3704,4101,4201,4202,4203,4204,4601,4602,4603,4604,4701,4702,4703,4704,5101,5102,5103,5201,5202,5203,5204,5301,5302,5303,5304,5401,5601,5602,5603,5701,5702,5703,5704,5801,5802,5803,5804,6101,6102,6201,6202,6301,6401,6402,"
				+ "4703,4704,5101,5102,5103,5201,5202,5203,5204,5301,5302,5303,5304,5401,5601,5602,5603,5701,5702,5703,5704,5801,5802,5803,5804,6101,6102,6201,6202,6301,6401,6402";
		bufferSQL.append("select a.TRANSACTIONTYPEID, b.scode clientcode,c.name cpname, d.name transname,e.code acccode,a.transactionstartdate,a.amount,a.code transcode \n ");
		bufferSQL.append(" from  SEC_ApplyForm   a, client b, sec_counterpart c, sec_transactiontype d, sec_account e \n ");
		bufferSQL.append(" where a.transactiontypeid in (");
		bufferSQL.append(strTransTypeFieldName);
		bufferSQL.append(") \n ");
		bufferSQL.append(" and a.statusid in (1,2) \n ");
		bufferSQL.append(" and a.clientid = b.id and c.id = a.Counterpartid(+) and  a.ACCOUNTID = e.id(+) and a.TransactionTypeID = d.id \n ");
		bufferSQL.append(" and( ( \n ");
		bufferSQL.append(" a.TRANSACTIONSTARTDATE >= " + strCurrentDate + " and a.TRANSACTIONSTARTDATE + nvl(a.LIQUIDATERATE,0) = " + strForecastDate + " \n ");
		bufferSQL.append(" )or( \n ");
		bufferSQL.append(" a.TRANSACTIONSTARTDATE < " + strCurrentDate + " and a.TRANSACTIONENDDATE > " + strCurrentDate + " and  a.TRANSACTIONSTARTDATE + nvl(a.LIQUIDATERATE,0) + nvl(a.term,0) = " + strForecastDate + " \n ");
		bufferSQL.append(" )) \n ");
		bufferSQL.append(" and a.OfficeID =" + officeID + " and a.CurrencyID =" + currencyID + " \n ");
		bufferSQL.append(" union \n ");
		bufferSQL.append(" select a.TRANSACTIONTYPEID, b.scode clientcode,c.name cpname,d.name transname,e.code acccode,a.valuedate,a.amount,a.code transcode \n ");
		bufferSQL.append(" from sec_deliveryorder a,client b, sec_counterpart c, sec_transactiontype d, sec_account e \n ");
		bufferSQL.append(" where a.deliverydate= " + strForecastDate + " and a.transactiontypeid in (" + strTransTypeFieldName + ") \n ");
		bufferSQL.append(" and a.clientid = b.id and c.id = a.Counterpartid(+) and  a.ACCOUNTID = e.id(+) and a.TransactionTypeID = d.id \n ");
		ArrayList resList;
		try 
		{
			initDAO();
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			// localPS.setTimestamp(1, extractDate);
			// localPS.setTimestamp(2, forecastDate);
			// localPS.setTimestamp(3, extractDate);
			// localPS.setTimestamp(4, extractDate);
			// localPS.setTimestamp(5, forecastDate);
			// localPS.setTimestamp(6, forecastDate);
			ResultSet localRS = executeQuery();
			resList = new ArrayList();
			String direction = null;
			while (localRS != null && localRS.next())
			{
				ForecastAmountInfo info = new ForecastAmountInfo();
				info.setForecastAmount(localRS.getDouble("amount"));
				info.setClientCode(localRS.getString("clientcode"));
				info.setContractCode(localRS.getString("transcode"));
				info.setCounterpartName(localRS.getString("cpName"));
				info.setAccountCode(localRS.getString("acccode"));
				info.setAccountTypeId(localRS.getLong("transactiontypeid"));
				info.setTransactionName(localRS.getString("transName"));
				
				direction = NameRef.getCapitalDirectionByTransactionTypeID(localRS.getLong("transactiontypeid"));
				if( direction != null && direction.equals("+") )
				{
					info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				}
				else if( direction != null && direction.equals("-") )
				{
					info.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				}
				
				info.setRemark(info.getContractCode());
				info.setOfficeID(officeID);
				info.setCurrencyID(currencyID);
				info.setModuleID(TPConstant.ModuleType.SECURITIES);
				info.setTransactionDate(forecastDate);
				
				resList.add(info);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return resList;
	}


	/**
	 * 资产回购收/付款 选取有关的申请/合同数据作为结果集
	 * 
	 * @param type:
	 *            资产回购
	 */
	public Collection getCapitalRepurchaseForecastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forecastDate, long transTypeID) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();
		String forcastDateFieldName = "";
		String transName = "";
		if (transTypeID == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY) 
		{
			forcastDateFieldName = "transactionstartdate +1";
		}
		else if (transTypeID == SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY) 
		{
			forcastDateFieldName = "transactionenddate";
		}

		bufferSQL.append("select b.scode,c.name, a.TransactionTypeID,a.transactionstartdate,a.amount,a.code" + " from SEC_ApplyForm a,client b, sec_counterpart c, sec_transactiontype d, sec_account e \n");
		bufferSQL.append(" where a.transactiontypeid in (7101) and a.statusid in (1) \n ");
		bufferSQL.append(" and a." + forcastDateFieldName + " = ? and a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID + " \n ");
		bufferSQL.append(" and a.clientid = b.id and a.counterpartid = c.id ");

		bufferSQL.append(" union \n ");
		bufferSQL.append(" select b.scode,c.name, a.TransactionTypeID,a.transactionstartdate,a.amount,a.code \n ");
		bufferSQL.append(" from SEC_ApplyContract a,client b, sec_counterpart c, sec_transactiontype d, sec_account e \n ");
		bufferSQL.append(" where a.transactiontypeid in (7101) and a.statusid in (1)  \n ");
		bufferSQL.append(" and a." + forcastDateFieldName + " = ? and a.OfficeID = " + officeID + " and a.CurrencyID =" + currencyID + " \n ");
		bufferSQL.append(" and a.clientid = b.id and a.counterpartid = c.id ");

		ArrayList resList;
		try 
		{
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			localPS.setTimestamp(1, forecastDate);
			localPS.setTimestamp(2, forecastDate);
			ResultSet localRS = executeQuery();
			resList = new ArrayList();
			while (localRS.next()) {
				ForecastAmountInfo info = new ForecastAmountInfo();
				info.setForecastAmount(localRS.getDouble("amount"));
				info.setClientCode(localRS.getString("clientcode"));
				info.setContractCode(localRS.getString("code"));
				info.setCounterpartName(localRS.getString("cpName"));
				info.setAccountCode(localRS.getString("acccode"));
				info.setAccountTypeId(localRS.getLong("transactiontypeid"));
				info.setTransactionName(localRS.getString("transName"));
				info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				info.setRemark(info.getContractCode());
				info.setOfficeID(officeID);
				info.setCurrencyID(currencyID);
				info.setModuleID(TPConstant.ModuleType.SECURITIES);
				info.setTransactionDate(forecastDate);
				resList.add(info);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return resList;
	}


	/**
	 * 委托理财和债券承销 ENTRUST_FINANCING BOND_UNDERWRITING
	 * 
	 */
	/**
	 * 资产回购收/付款 选取有关的申请/合同数据作为结果集
	 * 
	 * @param type:
	 *            资产回购
	 */
	public Collection getENTRUST_FINANCINGAndBOND_UNDERWRITINGForecastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forecastDate) throws Exception
	{

		StringBuffer bufferSQL = new StringBuffer();

		bufferSQL.append("select * from (select a.* from sec_ContractPlan a, \n");
		bufferSQL.append(" (select contractid,max(planversion) planversion from sec_ContractPlan group by contractid ) b \n ");
		bufferSQL.append(" where a.contractid=b.contractid and a.planversion=b.planversion and a.contractid \n ");
		bufferSQL.append(" in (select id from sec_ApplyContract where OfficeID =" + officeID + " and CurrencyID =" + currencyID + " and TransactionTypeID in (7301,8101) \n ");
		bufferSQL.append(" and StatusID in (1,2,3,4))) p, \n ");
		bufferSQL.append(" (select * from sec_ContractPlandetail where paytypeid=1 and plandate= ?) d where d.PlanVersionID=p.id \n ");

		ArrayList resList;
		try 
		{
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			localPS.setTimestamp(1, forecastDate);
			ResultSet localRS = executeQuery();
			resList = new ArrayList();
			while (localRS.next()) {
				ForecastAmountInfo info = new ForecastAmountInfo();
				long applyID = localRS.getLong("applyid");

				getCodeByApplyFormID(applyID, info);
				info.setForecastAmount(localRS.getDouble("amount"));
				long payTypeID = localRS.getLong("paytypeid");
				if (payTypeID == 1)
					info.setAmountDirection(TPConstant.AmountDirection.PLUS);
				else
					info.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				info.setRemark(info.getContractCode());
				info.setOfficeID(officeID);
				info.setCurrencyID(currencyID);
				info.setModuleID(TPConstant.ModuleType.SECURITIES);
				info.setTransactionDate(forecastDate);
				resList.add(info);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return resList;
	}


	// 到期还本
	public Collection getMaturityPayOffForecastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forecastDate) throws Exception
	{

		ArrayList resList = new ArrayList();
		String strExtractDate = transferTimestampToTo_DateString(extractDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);
		String sql1 = "select id,name,InterestRate,Interestcycle from sec_securities where  MaturityDate = " + strForecastDate;
		ArrayList secIDs;
		try 
		{
			prepareStatement(sql1);
			ResultSet localRS1 = executeQuery();
			secIDs = new ArrayList();

			while (localRS1.next()) 
			{
				SecuritiesInfo sInfo = new SecuritiesInfo();
				String Interestcycle = localRS1.getString("Interestcycle");
				if (Interestcycle != null)
					Interestcycle = Interestcycle.trim();
				if (Interestcycle == null || Interestcycle.length() == 0)
					continue;
				sInfo.setInterestcycle(Interestcycle);
				sInfo.setInterestRate(localRS1.getDouble("InterestRate"));
				sInfo.setId(localRS1.getLong("id"));
				sInfo.setName(localRS1.getString("name"));
				secIDs.add(sInfo);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		StringBuffer bufferSQL = new StringBuffer();
		ArrayList forecastAmounts = new ArrayList();
		for (int i = 0; i < secIDs.size(); i++) 
		{
			SecuritiesInfo tmpSec = (SecuritiesInfo) secIDs.get(i);
			long secID = tmpSec.getId();
			bufferSQL.append("select clientid,accountid,sum(Quantity) from \n");
			bufferSQL.append(" (select 't1' f1, id,clientid,accountid,Quantity from SEC_SecuritiesStock where securitiesid = " + secID);
			bufferSQL.append(" union \n ");
			bufferSQL.append(" select 't2' f1,a.id,clientid,accountid,decode(b. StockDirection,1,1,2,-1)*Quantity \n ");
			bufferSQL.append(" from SEC_ApplyForm a,sec_transactiontype b where a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID);
			bufferSQL.append(" and a.securitiesid= " + secID + " and a.statusid in (1,2) and a.transactiontypeid=b.id \n ");
			bufferSQL.append(" union \n ");
			bufferSQL.append(" select 't3' f1,a.id,clientid,accountid,decode(b. StockDirection,1,1,2,-1)*Quantity \n ");
			bufferSQL.append(" from SEC_deliveryorder  a,sec_transactiontype b where a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID);
			bufferSQL.append(" and a.securitiesid= " + secID + " and a.statusid >0 and a.transactiontypeid=b.id \n ");
			bufferSQL.append(" and deliverydate> " + strExtractDate + ")) group by clientid,accountid \n ");
			try
			{
				prepareStatement(bufferSQL.toString());
				ResultSet localRS2 = executeQuery();
				while (localRS2.next()) 
				{
					ForecastAmountInfo tmpFA = new ForecastAmountInfo(officeID, currencyID, extractDate, forecastDate, Constant.ModuleType.SECURITIES);
					tmpFA.setSecuritiesName(((SecuritiesInfo) secIDs.get(i)).getName());
					tmpFA.setAccountCode(getAccountCodeByID(localRS2.getLong("accountid")));
					tmpFA.setClientCode(getClientCodeByID(localRS2.getLong("clientid")));
					tmpFA.setAccountTypeId(3204);
					tmpFA.setTransactionName("到期还本");
					// Quantity*100*(1+证券利率InterestRate/付息周期Interestcycle)
					double quantities = localRS2.getLong("Quantity");
					String Interestcycle = tmpSec.getInterestcycle();
					int nInterestcycle = Integer.parseInt(Interestcycle);
					if (nInterestcycle == 0)
						continue;
					tmpFA.setForecastAmount(quantities * (1 + tmpSec.getInterestRate() / nInterestcycle));
					tmpFA.setRemark(tmpFA.getSecuritiesName() + "到期还本");
					resList.add(tmpFA);
				}
			}
			catch (ITreasuryDAOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (NumberFormatException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally
			{
				finalizeDAO();
			}
		}
		return resList;
	}


	// 预测兑息金额
	public Collection getReceivedInterestForecastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forecastDate) throws Exception
	{

		ArrayList resList = new ArrayList();
		String strExtractDate = transferTimestampToTo_DateString(extractDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);
		String sql1 = "select id,name,InterestRate,Interestcycle from sec_securities where  MaturityDate = " + strForecastDate;
		ArrayList secIDs;
		try 
		{
			prepareStatement(sql1);
			ResultSet localRS1 = executeQuery();
			secIDs = new ArrayList();

			while (localRS1.next())
			{
				SecuritiesInfo sInfo = new SecuritiesInfo();
				String Interestcycle = localRS1.getString("Interestcycle");
				if (Interestcycle != null)
					Interestcycle = Interestcycle.trim();
				if (Interestcycle == null || Interestcycle.length() == 0)
					continue;
				sInfo.setInterestcycle(Interestcycle);
				sInfo.setInterestRate(localRS1.getDouble("InterestRate"));
				sInfo.setId(localRS1.getLong("id"));
				sInfo.setName(localRS1.getString("name"));
				secIDs.add(sInfo);
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}

		StringBuffer bufferSQL = new StringBuffer();
		ArrayList forecastAmounts = new ArrayList();
		for (int i = 0; i < secIDs.size(); i++)
		{
			SecuritiesInfo tmpSec = (SecuritiesInfo) secIDs.get(i);
			long secID = tmpSec.getId();
			bufferSQL.append("select clientid,accountid,sum(Quantity) from \n");
			bufferSQL.append(" (select 't1' f1, id,clientid,accountid,Quantity from SEC_SecuritiesStock where securitiesid = " + secID);
			bufferSQL.append(" union \n ");
			bufferSQL.append(" select 't2' f1,a.id,clientid,accountid,decode(b. StockDirection,1,1,2,-1)*Quantity \n ");
			bufferSQL.append(" from SEC_ApplyForm a,sec_transactiontype b where a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID);
			bufferSQL.append(" and a.securitiesid= " + secID + " and a.statusid in (1,2) and a.transactiontypeid=b.id \n ");
			bufferSQL.append(" union \n ");
			bufferSQL.append(" select 't3' f1,a.id,clientid,accountid,decode(b. StockDirection,1,1,2,-1)*Quantity \n ");
			bufferSQL.append(" from SEC_deliveryorder  a,sec_transactiontype b where a.OfficeID = " + officeID + " and a.CurrencyID = " + currencyID);
			bufferSQL.append(" and a.securitiesid= " + secID + " and a.statusid >0 and a.transactiontypeid=b.id \n ");
			bufferSQL.append(" and deliverydate> " + strExtractDate + ") group by clientid,accountid \n ");
			try 
			{
				prepareStatement(bufferSQL.toString());
				ResultSet localRS2 = executeQuery();
				while (localRS2.next()) {
					long accountID = localRS2.getLong("accountid");
					long clientID = localRS2.getLong("clientid");

					if (isSubmitReceieveInterestDO(officeID, currencyID, secID, strForecastDate, clientID, accountID))
						continue;

					ForecastAmountInfo tmpFA = new ForecastAmountInfo(officeID, currencyID, extractDate, forecastDate, Constant.ModuleType.SECURITIES);
					tmpFA.setSecuritiesName(((SecuritiesInfo) secIDs.get(i)).getName());
					tmpFA.setAccountCode(getAccountCodeByID(accountID));
					tmpFA.setClientCode(getClientCodeByID(clientID));
					tmpFA.setAccountTypeId(3202);
					tmpFA.setTransactionName("银行间国债卖出");
					// Quantity*100*(1+证券利率InterestRate/付息周期Interestcycle)
					double quantities = localRS2.getLong("Quantity");
					String Interestcycle = tmpSec.getInterestcycle();
					int nInterestcycle = Integer.parseInt(Interestcycle);
					if (nInterestcycle == 0)
						continue;
					tmpFA.setForecastAmount(quantities * (1 + tmpSec.getInterestRate() / nInterestcycle));
					tmpFA.setRemark(tmpFA.getSecuritiesName() + "银行间国债卖出");
					resList.add(tmpFA);
				}
			}
			catch (ITreasuryDAOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (NumberFormatException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (SQLException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally
			{
				finalizeDAO();
			}
		}
		return resList;
	}


	/**
	 * 已提交了兑息交割单否
	 */
	private boolean isSubmitReceieveInterestDO(long officeID, long currencyID, long securitiesID, String forecastDate, long clientID, long accountID) throws Exception
	{

		boolean res;
		try
		{
			String sql = " select id from SEC_deliveryorder where OfficeID = " + officeID + " and CurrencyID = " + currencyID;
			sql += " and securitiesid = " + securitiesID;
			sql += " and a.statusid >0 and a.transactiontypeid in(2203,3203,3403,3703,4203,4703,5303) and trandactiondate = " + forecastDate;
			sql += " and clientid = " + clientID;
			sql += " and accountid = " + accountID;
			prepareStatement(sql);
			res = false;
			ResultSet localRS = executeQuery();
			if (localRS.next())
				res = true;
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}

		return res;
	}


	/**
	 * @return res1: clintcode res2: couterpartname
	 */
	private void getCodeByApplyFormID(long applyFormID, ForecastAmountInfo info) throws Exception
	{

		String strSQL = "select t.name transname,a.transactiontypeid,cl.scode, co.name,a.code appCode from client cl, sec_counterpart co, sec_applyform a,sec_transactiontype t where a.id = " + applyFormID + " and a.clientid=cl.id and a.counterpartid = co.id and t.id = a.transactiontypeid";
		String[] res = {"", ""};
		try 
		{
			initDAO();
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) 
			{
				info.setClientCode(localRS.getString("scode"));
				info.setCounterpartName(localRS.getString("name"));
				info.setAccountTypeId(localRS.getLong("transactiontypeid"));
				info.setTransactionName(localRS.getString("transname"));
				info.setTransactionName(localRS.getString("transname"));
				info.setContractCode(localRS.getString("appCode"));
				info.setRemark(info.getContractCode());

			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}

	}


	public String getClientCodeByID(long id) throws Exception
	{

		String strSQL = "select code from sec_client where id = " + id;
		String code;
		try 
		{
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			code = "";
			if (localRS.next()) {
				code = localRS.getString("code");
			}
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return code;
	}


	private String getAccountCodeByID(long id) throws Exception
	{

		String strSQL = "select code from sec_account where id = " + id;
		String code;
		try
		{
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			code = "";
			if (localRS.next()) {
				code = localRS.getString("code");
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return code;
	}


	static public void main(String[] args)
	{

		try 
		{
			Connection tpConn = Database.getConnection();
			Sec_ExtractorUtilDAO dao = new Sec_ExtractorUtilDAO();
			Collection c = dao.getRepurchaseForecastAmount(1, 1, Timestamp.valueOf("2004-08-20 00:00:00.000000000"), Timestamp.valueOf("2004-08-28 00:00:00.000000000"), 2);
			tpConn.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
