	/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-14
 */
package com.iss.itreasury.treasuryplan.etl.transform.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.extract.dao.AbstractAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.AbstractBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.report.dao.AbstractDestinationDataDAO;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPForecastDataDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.SystemParameterInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AbstractTransformer {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	protected int extractingType = -1;
	

	
	/**
	 * 抽取操作的类型
	 * */
	//抽取金额
	public final static int Extracting_Type_Actual  = 1;
	//抽取余额
	public final static int Extracting_Type_Forecast = 2;
	
	
	/**
	 * 
	 * @param officeID
	 * @param currencyID
	 * @param currentDate
	 * @param forecastDate
	 * @param tpConn
	 * @throws Exception
	 */
	public void transform(long officeID, 
			              long currencyID,
			              Timestamp startDate, 
						  Timestamp endDate, 
						  Connection tpConn)
			throws Exception {
		
		System.out.println("------------- 开始数据转换操作............................ ");
		System.out.println("-------startDate:"+startDate.toString());
		System.out.println("-------endDate:"+endDate.toString());
		Trea_TPTemplateDAO TPTemplateDAO = new Trea_TPTemplateDAO(tpConn);
		AbstractDestinationDataDAO destinationDataDAO = AbstractDestinationDataDAO.getAmountDAOInstance(extractingType, tpConn);
		AbstractAmountDAO amountDAO = AbstractAmountDAO.getAmountDAOInstance(extractingType,tpConn);
		Trea_SystemParameterDAO systemParameterDAO = new Trea_SystemParameterDAO(tpConn);
		AbstractBalanceDAO balanceDAO = AbstractBalanceDAO.getBalanceDAOInstance(extractingType, tpConn);
		
		Collection c = TPTemplateDAO.findTPTemplateByOfficeIDAndCurrencyID(officeID, currencyID);
		
		//第一次：每一条行项目来一次循环
		if(c!=null&&c.size()>0)
		{
				System.out.println("wzp ..............."+(c.size() ));
				//第二次：在更新区间内，每一天来一次循环
			Timestamp runDate = startDate;
			Timestamp executeDate = Env.getSystemDate();
			while(!runDate.after(endDate))
			{
			    boolean isInsert = true;
				if(destinationDataDAO.checkDataOneDay(runDate,officeID, currencyID)==1)
				{
					isInsert = false;
				}
				
				Iterator it = c.iterator();
				while (it.hasNext())
				{
				    System.out.println("wzp ..............."+(runDate ));
					TPTemplateInfo tpTemplateInfo = (TPTemplateInfo) it.next();
					//TPForecastDataInfo forecastDataInfo;
					
					//取得一条行项目所有取数逻辑的金额，并汇总到这条行项目下面
					sumTransformationData(officeID, 
							              currencyID, 
							              runDate, 
							              executeDate,
							              tpConn, 
										  destinationDataDAO, 
										  amountDAO,
							              systemParameterDAO,
										  balanceDAO, 
										  tpTemplateInfo,
										  false,
										  true,
										  isInsert);
				}
		
				//向上汇总
				sumAmountDependOnLineLevel(officeID, 
						                   currencyID, 
						                   runDate, 
										   destinationDataDAO,
										   false);
				
				//在表Trea_ActualData 或者 Trea_ForcecastData中查询，取数逻辑包含"#"的行项目的 某一天的数据的集合	
				Collection c3 = destinationDataDAO.getDataThatNotSum(officeID,
							currencyID, /*currentDate, */runDate);
				
				Iterator it3 = c3.iterator();
				while (it3.hasNext()) 
				{
					TPDestinationDataInfo found1 = (TPDestinationDataInfo) it3.next();
					TPTemplateInfo templateInfo2 = (TPTemplateInfo) TPTemplateDAO.findByID(found1.getLineID(), TPTemplateInfo.class);
					sumTransformationData(officeID, 
							              currencyID, 
							              runDate, 
										  executeDate, 
										  tpConn, 
										  destinationDataDAO, 
										  amountDAO, 
										  systemParameterDAO, 
										  balanceDAO, 
										  templateInfo2,
										  true,
										  true,
										  false);
				}
				
				runDate = DataFormat.getNextDate(runDate, 1);
			}
		}
			//destinationDataDAO.updateAfterDailyTransform(forecastDate);
	}
	/**
	 * @param officeID
	 * @param currencyID
	 * @param forecastDate
	 * @param destinationDataDAO
	 * @throws Exception
	 */
	public void sumAmountDependOnLineLevel(long officeID,
			long currencyID, 
			Timestamp forecastDate,
			AbstractDestinationDataDAO destinationDataDAO,
			boolean isPlanAmount
			) throws Exception {
		Timestamp inputTime = null;
		if(isPlanAmount){
			destinationDataDAO.setAmountFieldNameAsPlanAmount();
			inputTime = DataFormat.getTreasuryPlanExecuteDate();
		}
		long levelCount = destinationDataDAO.getLevelCountByCondition(officeID,currencyID,/*currentDate,*/forecastDate);
		System.out.println("--------一共:"+levelCount);
		for (long i = levelCount-1; i >= 1; i--) 
        {
			
			System.out.println("--------第:"+i+"行---------");
			Collection c2 = destinationDataDAO.getSameLevelLeafDestinationData(
					officeID, 
					currencyID, 
					//currentDate,  
					forecastDate, 
					i);
                    
            Iterator it2 = null; 
            if( c2 != null && c2.size() > 0)
            {
                it2 = c2.iterator();
            } 
            
			while (it2 != null && it2.hasNext()) 
            {
				Long lineID = (Long) it2.next();
				System.out.println("------lineID.longValue():"+lineID.longValue());
				double sumAmount = destinationDataDAO.sumAmountOfSubItems(
						officeID,
						currencyID,
						//currentDate, 
						forecastDate, 
						lineID.longValue());
				
					if(sumAmount > 0)
						destinationDataDAO.updateAmountByTransactionDateAndLineID(officeID, currencyID,forecastDate,lineID.longValue(),sumAmount,inputTime);							

			    }
			//destinationDataDAO.updateAmountLevel(officeID,currencyID,forecastDate,i,inputTime);

			}
		destinationDataDAO.resetAmountFieldName();
        
		
	}
	
	
	/**
	 * @param officeID
	 * @param currencyID
	 * @param forecastDate
	 * @param destinationDataDAO
	 * @throws Exception
	 */
	public void sumAmountDependOnLineID(long officeID,
			long currencyID, 
			Timestamp forecastDate,
			AbstractDestinationDataDAO destinationDataDAO,
			boolean isPlanAmount,Vector v
			) throws Exception {
		Timestamp inputTime = null;
		
		if(isPlanAmount){
			destinationDataDAO.setAmountFieldNameAsPlanAmount();
			inputTime = DataFormat.getTreasuryPlanExecuteDate();
		}
		
		long levelCount=0;
		long i = 1;
		long parentlineid = -1;
		long lineid = -1;
		double sumAmount=0;
		
		if (v!=null)
		{	System.out.println("--------v.size=:"+v.size());	
			Iterator it = v.iterator();
			while (it.hasNext()) 
			{
				TPForecastDataInfo info = (TPForecastDataInfo) it.next();
					
				System.out.println("--------levelCount="+info.getLineLevel());
				
				if (info.getIsLeaf()==1)
				{
					levelCount = info.getLineLevel();
				}
				else
				{
					levelCount=0;
				}
				
				lineid = info.getLineID();
					
				for (i = levelCount; i > 1; i--) 
				{
					parentlineid = destinationDataDAO.getSameLevelParentLineID(officeID,currencyID,forecastDate,i,lineid);

					if (parentlineid>0)
					{
						sumAmount = destinationDataDAO.sumAmountOfSubItems(officeID,currencyID,forecastDate,parentlineid);
							
						destinationDataDAO.updateAmountByTransactionDateAndLineID(officeID, currencyID,forecastDate,parentlineid,sumAmount,inputTime);							
					}
						
					lineid = parentlineid;
				}						
			}
		}
		
		destinationDataDAO.resetAmountFieldName();
	}

	/**
	 * 取得一条行项目所有取数逻辑的金额，并汇总到这条行项目下面
	 * @param officeID
	 * @param currencyID
	 * @param runDate
	 * @param executeDate
	 * @param tpConn
	 * @param destinationDataDAO
	 * @param forecastAmountDAO
	 * @param systemParameterDAO
	 * @param forecastBalanceDAO
	 * @param tpTemplateInfo
	 * @param isNeedSumSubItem
	 * @param isTransform
	 * @param insertOrUpdate
	 * @throws ITreasuryDAOException
	 * @throws Exception
	 */
	public void sumTransformationData(long officeID, 
								long currencyID,
								Timestamp runDate, 
								Timestamp executeDate, 
								Connection tpConn,
								AbstractDestinationDataDAO destinationDataDAO,
								AbstractAmountDAO forecastAmountDAO,
								Trea_SystemParameterDAO systemParameterDAO,
								AbstractBalanceDAO forecastBalanceDAO,
								TPTemplateInfo tpTemplateInfo,
								boolean isNeedSumSubItem,
								boolean isTransform,
								boolean insertOrUpdate) 
						        throws ITreasuryDAOException,Exception 
    {
		System.out.println("----------The exexuted TPTemplateInfo is:"+tpTemplateInfo);
		//计算实际期初余额使用
		Timestamp beoforeTransDate = DataFormat.getNextDate(runDate,-1);
		
		if(tpTemplateInfo.getId() == 409 || tpTemplateInfo.getId() == 409){
			System.out.println("----------The exexuted TPTemplateInfo is 409 or 409");			
		}
		
		
		TPDestinationDataInfo dataInfo = TPDestinationDataInfo.getDestinationDataInstance(extractingType, tpTemplateInfo);
		if(isTransform)
			dataInfo.setIsTransformation(TPConstant.TRUE);
		else
			dataInfo.setIsTransformation(TPConstant.FALSE);
		dataInfo.setTransactionDate(runDate);
		dataInfo.setExecuteDate(executeDate);
		dataInfo.putDestinationAmount(0);
		if (tpTemplateInfo.getIsLeaf() == TPConstant.FALSE) 
        {
		    System.out.println("@@@@@  ........................ is not leaf!!! ");
		} 
        else 
        {
			System.out.println("@@@@@  ........................ is  leaf!!! ");
            
            Trea_TPTemplateItemDAO templateItemDAO = new Trea_TPTemplateItemDAO(tpConn);
            
			//TPTemplateItemInfo templateItemInfoCondition = new TPTemplateItemInfo();
			//templateItemInfoCondition.setLineID(tpTemplateInfo.getId());
			Collection c1 = templateItemDAO.findAllByLineID(tpTemplateInfo.getId());
			
			Iterator it1 = null;
            if( c1 != null )
            {
                it1 = c1.iterator();
            }
			double forecastAmount = 0.0;
			double itemValue = 0.0;
            
			while (it1 != null && it1.hasNext()) 
            {
				double tmpPayment=0;
				tmpPayment=0;
				TPTemplateItemInfo foundItemInfo = (TPTemplateItemInfo) it1.next();
                
				System.out.println("--------foundItemInfo:"+foundItemInfo.toString());
				String param = foundItemInfo.getParameter();
				System.out.println("-----------param:"+param);	
				System.out.println("-----------AmountFlag:"+foundItemInfo.getAmountFlag());
				
				if (param == null) 
                {
					if (foundItemInfo.getAmountFlag() == TPConstant.AmountFlag.Amount)
                    {
						//1.取发生额
						//根据一条取数逻辑，去Trea_ActualAmunt / Trea_ForecastAmount中，计算一条取数逻辑某一天的金额
						itemValue = (forecastAmountDAO .sumAmountDependOnTemplateItem(
										officeID, currencyID/*, currentDate*/,
										runDate,foundItemInfo))/10000.0;
					} 
                    else //2.取余额 
                    {
						Timestamp queryDate = null;
						if(tpTemplateInfo.getMaintenanceFlag() == TPConstant.LineType.Balance_Begin )
                        { 
							queryDate = new Timestamp(beoforeTransDate.getTime());
						}else{
							if(foundItemInfo.getAmountFlag()==TPConstant.AmountFlagForSetting.Yesterday_Balance){
								queryDate = new Timestamp(beoforeTransDate.getTime());
							}else{
								queryDate = new Timestamp(runDate.getTime());
							}
						}
						System.out.println("-----------queryDate"+queryDate);						
						System.out.println("-----------forecastDate"+runDate);
						
						//根据一条取数逻辑，去Trea_ActualBalance / Trea_ForecastBalance中，计算一条取数逻辑某一天的余额
						itemValue = (forecastBalanceDAO
								.sumBalanceDependOnTemplateItem(
										officeID, currencyID/*, currentDate*/,
										queryDate,foundItemInfo))/10000.0;
						if(itemValue > 0 )
                        {
							System.out.println("-----------itemValue"+itemValue);	
						}
					}
				} 
                else if (param.startsWith("#")) //3.取其他行项目的数值
                {
                	System.out.println("#################      isNeedSumSubItem = "+isNeedSumSubItem);
                	
                	//if(isNeedSumSubItem)
                    //{
						param = param.substring(1,param.length());
						System.out.println("#################");	
						long lineID = Long.parseLong(param);
						System.out.println("-----start with # Reference lineid is:"+lineID);						
						itemValue = destinationDataDAO.getAmountByCondition(
								officeID, currencyID, /*currentDate,*/ runDate,
								lineID);
						System.out.println("-----------Reference line value"+itemValue);
								
					//}
//					continue;
				} 
                else if (param.startsWith("@reserve")) //4.存款准备金
                {
					System.out.println("-----------Reference line reserve"+itemValue);
					SystemParameterInfo reserveParameterInfo = new SystemParameterInfo();
					reserveParameterInfo.setEffectiveDate(runDate);
					reserveParameterInfo.setStatusID(1);
					reserveParameterInfo.setParameterName("Reserve");
					reserveParameterInfo.setOfficeID(officeID);
					reserveParameterInfo = systemParameterDAO
							.findLastEffectiveDate(reserveParameterInfo);
					itemValue = reserveParameterInfo.getParameterValue();
					System.out.println("-----------@reserve"+itemValue);	
				} 
                else if (param.startsWith("@paymentrate")) //5.必要备付金率
                {
					System.out.println("-----------Reference line paymentrate"+itemValue);					
					SystemParameterInfo reserveParameterInfo = new SystemParameterInfo();
					reserveParameterInfo.setStatusID(1);
					reserveParameterInfo.setEffectiveDate(runDate);
					reserveParameterInfo.setOfficeID(officeID);
					reserveParameterInfo.setParameterName("Paymentrate");
					reserveParameterInfo = systemParameterDAO
							.find(reserveParameterInfo);
					itemValue = reserveParameterInfo.getParameterValue()/100.0;
					
					/////////////////////
					reserveParameterInfo = new SystemParameterInfo();
					reserveParameterInfo.setStatusID(1);
					reserveParameterInfo.setEffectiveDate(runDate);
					reserveParameterInfo.setParameterName("Paymentrate");
					reserveParameterInfo = systemParameterDAO.find(reserveParameterInfo);
					///////////////////////////
					tmpPayment= reserveParameterInfo.getParameterValue();
					
					
					
					
					System.out.println("-----------@Paymentrate"+itemValue);
				} 
                else //6.固定金额
                {					
					itemValue = Double.valueOf(param).doubleValue();
					System.out.println("-----------No Prefix"+itemValue);					
				}
				System.out.println("-------itemValue11:"+itemValue);
				if (foundItemInfo.getCalculateFlag() != null) 
                {
					System.out.println("-------有计算标志 CalculateFlag:"+foundItemInfo.getCalculateFlag());					
					if (foundItemInfo.getCalculateFlag().trim().compareTo("-") == 0) {
						forecastAmount -= itemValue;
					} else if (foundItemInfo.getCalculateFlag().trim()
							.compareTo("*") == 0) {
						forecastAmount *= itemValue;
					} else if (foundItemInfo.getCalculateFlag().trim()
							.compareTo("/") == 0) {
						if((String.valueOf(itemValue)).compareTo("0") == 0){
							itemValue=1;							
						}
							forecastAmount /=itemValue;		
							System.out.println(forecastAmount+"---+++------forecastAmount:^^^^^^^^^^"+itemValue);						
					} else {
						forecastAmount += itemValue;
					}
					//只有必要备付金时tmpPayment才可能不为0
					forecastAmount+=tmpPayment;
				} 
                else 
                {
					forecastAmount += itemValue;
				}
			}
			System.out.println("---------forecastAmount:^^^^^^^^^^"+forecastAmount);
			if(forecastAmount > 0){
				dataInfo.putDestinationAmount(forecastAmount);
			}else{
				dataInfo.putDestinationAmount(0);
			}
		}
		System.out.println("---------forecastDataInfo:--------------");
		if(insertOrUpdate)
        {
			//destinationDataDAO.save(dataInfo);
			destinationDataDAO.add(dataInfo);
		}
        else
        {
			destinationDataDAO.updateByPK(dataInfo);
		}
		
	}

/*	
	static public void main(String[] agrs){
		try {
			long start = System.currentTimeMillis();			
			Connection tpConn = Database.getConnection();
			//tpConn.setAutoCommit(false);
			Timestamp date = Timestamp.valueOf("2004-05-28 00:00:00.000000000");			
			Timestamp today = Timestamp.valueOf("2004-07-28 00:00:00.000000000");
//hh:mm:ss.fffffffff
			//ForecastDataTransformer f = new ForecastDataTransformer();
			//Timestamp lastDay = DataFormat.getNextDate(date, -1);
			ActualDataTransformer f = new ActualDataTransformer();
			for(int i= 0;i<1;i++){
				System.out.println("--------计算关机日是"+ date);		
				
			f.transform(1, 1,
					today, date, tpConn);
			date = DataFormat.getNextDate(date, 1);	

			}
			//tpConn.commit();
			tpConn.close();
			long end = System.currentTimeMillis();
			long duration = end-start;
			System.out.println("---------duration:"+ duration);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/ 
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AbstractTransformer#transform(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	

	
	
	
}
