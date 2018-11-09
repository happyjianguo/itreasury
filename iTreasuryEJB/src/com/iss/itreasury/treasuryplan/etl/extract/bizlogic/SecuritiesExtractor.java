/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-7
 */
package com.iss.itreasury.treasuryplan.etl.extract.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sec_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ActualAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastAmountInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;


/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesExtractor extends AbstractExtractor 

{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractActualBalance(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception
	{

		// TODO Auto-generated method stub

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#eextractActualAmount(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualAmount(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		Sec_ExtractorUtilDAO utilDAO = new Sec_ExtractorUtilDAO();
		ArrayList list1 = (ArrayList) utilDAO.getActualAmountFromDeliveryOrders(startDate, endDate, officeID, currencyID);
		ArrayList list2 = (ArrayList) utilDAO.getActualAmountFromApplyForms(startDate, endDate, officeID, currencyID);
		list1.addAll(list2);
		log.debug("-------size-" + list1.size());
		Connection tpConn = utilDAO.getConnectionByModuleID(SETTConstant.ModuleType.PLAN);
		tpConn.setAutoCommit(false);
		Trea_ActualAmountDAO actualAmountDAO = new Trea_ActualAmountDAO(tpConn);
		actualAmountDAO.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.SECURITIES, officeID, currencyID);
		// actualAmountDAO.setAutoCommit(false);
		Iterator it = list1.iterator();
		while (it.hasNext()) 
		{
			ActualAmountInfo info = (ActualAmountInfo) it.next();
			log.debug("-------extractActualAmount-" + info.toString());
			info.setExecuteDate(Env.getSystemDate());
			actualAmountDAO.add(info);
		}
		tpConn.commit();
		// actualAmountDAO.commit();
		utilDAO.closeModuleConn(TPConstant.ModuleType.PLAN);
		utilDAO.closeModuleConn(TPConstant.ModuleType.SECURITIES);

	}


	/*
	 * 从证券模块抽取预测发生额
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractForcastAmount(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastAmount(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		System.out.println("从证券模块抽取预测发生额..............................");
		
		Sec_ExtractorUtilDAO utilDAO = new Sec_ExtractorUtilDAO();
		Connection tpConn = utilDAO.getConnectionByModuleID(SETTConstant.ModuleType.PLAN);
		tpConn.setAutoCommit(false);
		Trea_ForecastAmountDAO forecastAmountDAO = new Trea_ForecastAmountDAO(tpConn);
		forecastAmountDAO.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.SECURITIES, officeID, currencyID);
		tpConn.commit();
		
		ArrayList list = new ArrayList();
		
		Timestamp transDate = startDate;
		while (transDate != null && !transDate.after(endDate))
		{
			log.debug("111-------------------------------------------------------getRepurchaseForecastAmount..........");
			ArrayList l1 = (ArrayList) utilDAO.getRepurchaseForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate, 1);
			if (l1.size() > 0)
			{
				list.addAll(l1);
			}
			
			
			log.debug("222-------------------------------------------------------getRepurchaseForecastAmount..........");
			ArrayList l2 = (ArrayList) utilDAO.getRepurchaseForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate, 2);
			if (l2.size() > 0)
			{
				list.addAll(l2);
			}
			
			
			log.debug("333-------------------------------------------------------getPurchaseForecastAmount..........");
			ArrayList l3 = (ArrayList) utilDAO.getPurchaseForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate);
			if (l3.size() > 0)
			{
				list.addAll(l3);
			}
			
			
			log.debug("444-------------------------------------------------------getCapitalRepurchaseForecastAmount..........");
			ArrayList l4 = (ArrayList) utilDAO.getCapitalRepurchaseForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate, 7102);
			if (l4.size() > 0)
			{	
				list.addAll(l4);
			}
			
			
			log.debug("555-------------------------------------------------------getCapitalRepurchaseForecastAmount..........");
			ArrayList l5 = (ArrayList) utilDAO.getCapitalRepurchaseForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate, 7103);
			if (l5.size() > 0)
			{
				list.addAll(l5);
			}
			
			
			log.debug("666-------------------------------------------------------getENTRUST_FINANCINGAndBOND_UNDERWRITINGForecastAmount..........");
			ArrayList l6 = (ArrayList) utilDAO.getENTRUST_FINANCINGAndBOND_UNDERWRITINGForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate);
			if (l6.size() > 0)
			{
				list.addAll(l6);
			}
			
			
			log.debug("777-------------------------------------------------------getMaturityPayOffForecastAmount..........");
			ArrayList l7 = (ArrayList) utilDAO.getMaturityPayOffForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate);
			if (l5.size() > 0)
			{
				list.addAll(l7);
			}
			
			log.debug("888-------------------------------------------------------getReceivedInterestForecastAmount..........");
			ArrayList l8 = (ArrayList) utilDAO.getReceivedInterestForecastAmount(officeID, currencyID, Env.getSystemDate(), transDate);
			if (l8.size() > 0)
			{
				list.addAll(l8);
			}
			transDate =  DataFormat.getNextDate(transDate, 1);
		}

		if( list != null )
		{
			for (int i = 0; i < list.size(); i++) 
			{
				log.debug("-------" + list.get(i));
				ForecastAmountInfo info = (ForecastAmountInfo) list.get(i);
				info.setExecuteDate(Env.getSystemDate());
				
				log.debug("befor add !!!  ------ForecastAmountInfo  :  " + info.toString());
				forecastAmountDAO.add(info);
			}
		}
		tpConn.commit();
		utilDAO.closeModuleConn(TPConstant.ModuleType.PLAN);
		utilDAO.closeModuleConn(TPConstant.ModuleType.SECURITIES);
	}


	static public void main(String[] args)
	{

		SecuritiesExtractor extractor = new SecuritiesExtractor();
		try
		{
			if (!TPScheduler.isNeedExecute(null))
				return;
			extractor.startExtractData(args);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractForcastBalance(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception
	{

		// TODO Auto-generated method stub

	}

}
