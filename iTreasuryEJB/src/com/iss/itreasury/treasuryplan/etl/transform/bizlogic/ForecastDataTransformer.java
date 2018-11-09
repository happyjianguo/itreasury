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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.treasuryplan.etl.extract.dao.AbstractAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.AbstractBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.report.dao.AbstractDestinationDataDAO;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPForecastDataDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
/**
 * @author yehuang
 * 
 * To change the template for this generated type comm ent go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class ForecastDataTransformer extends AbstractTransformer {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	public ForecastDataTransformer(){
		extractingType = Extracting_Type_Forecast;
	}

	
	/**
	 * 开始转换数据
	 * @param args[0] 其实月份
	 * */
	public static void main(String[] args){
		System.out.println("--------"+args);
		Connection tpConn = null;
		try {
			if(!TPScheduler.isNeedExecute(null))
				return;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try
		{
			Trea_ForecastAmountDAO tfaDao = new Trea_ForecastAmountDAO();
			tfaDao.delAllCurrent();
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		
		
		if(args != null && args.length == 1 && args[0] != null){
			int monthInterval = Integer.valueOf(args[0]).intValue();
			try{
				Timestamp currentDate = DataFormat.getTreasuryPlanExecuteDate();//Env.getCurrentSystemDate();
				System.out.println("-----------今天是111: "+ currentDate);		
				System.out.println("---------------开始抽取数据-----------");
				Sett_ExtractorUtilDAO extractorUtilDAO = new Sett_ExtractorUtilDAO();
				Collection c = extractorUtilDAO.findAllOfficeTime();
				Iterator it = c.iterator();			
				ForecastDataTransformer transfomer = new ForecastDataTransformer();
				tpConn = Database.getConnection();
				tpConn.setAutoCommit(false);
				while(it.hasNext()){
					OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
					System.out.println("---------正在处理的OfficeTimeInfo: "+ officeTimeInfo);
					
					//如果系统状态为“关机”才运行资金计划
					long lStatusID = Constant.SystemStatus.getID(officeTimeInfo.getSSYSTEMSTATUSDESC());
					if (lStatusID!=Constant.SystemStatus.CLOSE)
					{
						break;
					}
					
					Timestamp startDate = officeTimeInfo.getDTOPENDATE();
					
					//交易时间更改为取系统时间
					startDate = DataFormat.getNextDate(startDate,1);
					startDate = DataFormat.getNextMonth(startDate,monthInterval);
					
					System.out.println("---------startDate: "+ startDate);
					Timestamp endDate = DataFormat.getNextMonth(startDate,1);
					endDate = DataFormat.getNextDate(endDate, -1);
					System.out.println("---------endDate: "+ endDate);
					
					//debug at here
					//startDate = Timestamp.valueOf("2004-08-20 00:00:00.000000000");
					//endDate = Timestamp.valueOf("2004-08-20 00:00:00.000000000");
					
					while(!startDate.after(endDate)){//处理一个月的预测数据
							
						transfomer.transform(officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),currentDate,startDate,tpConn);
						startDate = DataFormat.getNextDate(startDate, 1);
						//i++;
					}
					
				}
				tpConn.commit();
				tpConn.close();				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
				try {
					if(tpConn!=null)
					  tpConn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		}
	}

	}	

	

}
